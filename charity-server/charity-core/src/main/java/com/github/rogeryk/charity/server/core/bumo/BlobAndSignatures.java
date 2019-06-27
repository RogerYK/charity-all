package com.github.rogeryk.charity.server.core.bumo;

import io.bumo.model.response.result.data.Signature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlobAndSignatures {

    private String Blob;

    private Signature[] signatures;
}
