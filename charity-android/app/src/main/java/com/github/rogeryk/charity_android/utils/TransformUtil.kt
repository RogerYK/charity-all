package com.github.rogeryk.charity_android.utils

import android.content.Context
import android.graphics.*
import com.squareup.picasso.Transformation
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.renderscript.Element.U8_4
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.Allocation
import android.graphics.Bitmap
import android.renderscript.Element
import android.renderscript.RenderScript





class CircleTransform : Transformation {

    override fun key(): String {
        return "circle"
    }

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        squaredBitmap.recycle()
        return bitmap
    }

}

class BlurTransformation(context: Context) : Transformation {

    private var rs: RenderScript = RenderScript.create(context)

    override fun transform(bitmap: Bitmap): Bitmap {
        val blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        val input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED)
        val output = Allocation.createTyped(rs, input.type)

        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setInput(input)

        //设置模糊半径
        script.setRadius(25f)

        script.forEach(output)

        output.copyTo(blurredBitmap)

        bitmap.recycle()

        return blurredBitmap
    }

    override fun key(): String {
        return "blur"
    }
}
