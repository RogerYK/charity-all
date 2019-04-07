import React, { Component } from 'react'
import {Breadcrumb, Tabs, Button, Timeline, message} from 'antd'
import {Link} from 'react-router-dom'

import './style.scss'
import Header from '../../components/Header';
import Footer from '../../components/Footer';

import p1 from './500.jpg'
import Steps, {Step} from './Steps';

import c_1 from './c-1.jpg'
import c_2 from './c-2.jpg'
import c_3 from './c-3.jpg'
import c_4 from './c-4.jpg'
import b_1 from './b-1.png'
import a_1 from './a-1.jpg'
import { mountedScrollTop } from '../../utils';
import { getProjectDetail, donate } from '../../api';
import { getToken } from '../../utils/auth';


const TabPane = Tabs.TabPane


export default class DetailPage extends Component {

    constructor(props) {
        super(props)
        this.state = {
          project: {},
          donateStatus: 0,
          donateAmount: '',
        }
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        const id = this.props.match.params.id
        getProjectDetail(id).then(res => {
          console.log(res.data)
            this.setState({project:res.data})
        })
    }

    handDonateStatusChnage = (s) => {
      this.setState({
        donateStatus: s,
      })
    }

    handleAmountChange = (e) => {
      console.log(e)
      const value = e.target.value
      this.setState({donateAmount: value})
    }

    handleDonate = () => {
      if (!getToken()) {
        message.info('请先登陆')
        return
      }
      const status = this.state.donateStatus
      if (status === 0) {
        message.error('请选择捐款金额')
        return
      }
      let amount = 0
      if (status < 4) {
        const amounts = [0, 50, 100, 200]
        amount = amounts[status]
      } else {
        amount = this.state.donateAmount
      }
      donate(this.state.project.id, amount).then(res => {
        message.info('捐款成功')
      }).catch(res => {
        message.error('失败')
      })
    }

    render() {
      const project = this.state.project
      const donateStatus = this.state.donateStatus
      
        return (
            <div className='detail-page'>
                <Header />
                    <div className='content-container'>
                        <div className='content-top'>
                            <div className='title'>
                                <Breadcrumb >
                                    <Breadcrumb.Item><Link to='donation'>捐款</Link></Breadcrumb.Item>
                                    <Breadcrumb.Item><Link to='search'>项目列表</Link></Breadcrumb.Item>
                                    <Breadcrumb.Item> <span className='project-name'>{project.name}</span></Breadcrumb.Item>
                                </Breadcrumb>
                                <div className='share-links'>
                                    <span className='word'>分享到:</span>
                                    <span className='share-icon tc-weibo'></span>
                                    <span className='share-icon qq-space'></span>
                                    <span className='share-icon xl-weibo'></span>
                                    <span className='share-icon rr'></span>
                                    <span className='share-icon kx'></span>
                                </div>
                            </div>
                            <div className='project'>
                                <div className='project-img'>
                                    <div className='verification'></div>
                                    <div className='img' style={{backgroundImage: `url("${project.img}")`}}></div>
                                </div>
                                <div className='project-detail'>
                                    <div className='item project-status'>
                                        <div className='label'>状态</div>
                                        <div className='field'>
                                            <Steps style={{width: "300px"}}>
                                                <Step title='发起' active={true}/>
                                                <Step title='审核' active={true}/>
                                                <Step title='募款' active={true}/>
                                                <Step title='执行' />
                                                <Step title='结束' />
                                            </Steps>
                                        </div>
                                    </div>
                                    <div className='item raised-money'>
                                        <div className='label'>已筹</div>
                                        <div className='field'>
                                            <span className='num'>{project.raisedMoney}</span>元
                                        </div>
                                    </div>
                                    <div className='item time-range'>
                                        <div className='label'>时间</div>
                                        <div className='field'>{project.startTime} 至 {project.endTime}</div>
                                    </div>
                                    <div className='item donate-money'>
                                        <div className='label'>金额</div>
                                        <div className='field'>
                                            <div 
                                            onClick={() => this.handDonateStatusChnage(1)}
                                            className={`radio ${donateStatus === 1 ? 'active' : ''}`}
                                            >50元</div>
                                            <div 
                                            onClick={() => this.handDonateStatusChnage(2)}
                                            className={`radio ${donateStatus === 2 ? 'active' : ''}`}
                                            >100元</div>
                                            <div
                                            onClick={() => this.handDonateStatusChnage(3)}
                                            className={`radio ${donateStatus === 3 ? 'active' : ''}`}
                                             >200元</div>
                                            <div 
                                            onClick={() => this.handDonateStatusChnage(4)}
                                            className={`input-div ${donateStatus === 4 ? 'active' : ''}`}
                                            >
                                                <div className='addon'>其他</div>
                                                <input type="number" onChange={this.handleAmountChange} value={this.state.donateAmount} type='text' />
                                            </div>
                                        </div>
                                    </div>
                                    <div className='item detail-bottom'>
                                        <div onClick={this.handleDonate} className='donate-btn'>我要捐款</div>
                                        <div className='wechat-pay'>微信捐款</div>
                                        <div className='donation-info'>
                                            <div className='item'>
                                                <div className='label'>捐款人次:</div>
                                                <div className='field'><span className='num'>{project.donorCount}</span></div>
                                            </div>
                                            <div className='item'>
                                                <div className='label'>企业配捐:</div>
                                                <div className='field'><span className='num'>410350.02</span>元</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                        </div>
                        <div className='content-bottom'>
                            <div className='bottom-left'>
                                <Tabs defaultActiveKey="1">
                                    <TabPane className='project-content' tab="捐助说明" key="1">
                                        <h3>项目介绍</h3>
                                        <h4>温度，在城市与农村之间，演绎着不同的梦想</h4>
                                        <img src={c_1} alt="c-1" />
                                        <p>城市中，我们享受着温暖如春的室温，北京小学教室规定最低室温18℃。</p>
                                        <img src={c_2} alt="c-2" />
                                        <p>而在城乡的另一边，有我们看不到的寒冷……</p>
                                        <img src={c_3} alt="c-3" />
                                        <h4>孩子，你的世界我们曾路过</h4>
                                        <p>2015年4月，爱心衣橱前往江西婺源县炉坑岭小学进行新衣发放。十几里的山路绵延悠长…… 路的尽头是所学校，一所只有两位月薪800元的年轻女代课老师及三十几个孩子的学校。 童年是无忧的，面对我们，孩子们大都热情、开朗。在一群欢乐的孩子里面，穿着一件黄色棉衣的小男孩引起了我们的注意。不同于其他的孩子，男孩眉头紧锁，表情落寞，怯生生地望着我们，手里紧紧握着我们刚刚递过去的一块奶糖。</p>
                                        <img src={c_4} alt="c-4" />
                                        <h3>项目预算</h3>
                                        <p>A、公众筹款目标：该项目公众筹款目标为2615115.25元，已筹24495.30元，99预计用户筹款为2590619.95元。以下表格为99公益日期间预计筹款金额预算（不含配捐），我们将会为12800名筹集新衣定制费用及项目执行等费用，预算如下： </p>
                                        <img src={b_1} alt="b-1" />
                                        <p>B、配捐使用方向：企业和腾讯配捐部分的预算计划将主要用于爱心衣橱心暖新衣项目，为偏远高寒地区的孩子定制新衣，99公益日结束并确认了“企业和腾讯配捐”具体金额后15天内，再补充详细预算表。</p>
                                        <p>C、公募管理费： 中华社会救助基金会将在筹款结束后，收取项目最终筹款总额的3%作为公募管理费。（管理费将用于公募机构运营成本，如人员、水电、交通等费用）。</p>
                                        <p>D、善款使用预案：本项目“最低可执行金额”为198元，若最终所募善款少于“最低可执行金额”，善款的变更使用方向为爱心衣橱鲲鹏助学项目，为身处困境中的学子提供学习及生活补助。</p>
                                        <h3>执行计划</h3>
                                        <p>1、学校走访：自2018年9月开始，爱心衣橱志愿者将开始对偏远地区的村级小学及教学点的走访，收集资料，向爱心衣橱公益基金提交新衣申请；</p>
                                        <p>2、学校审核：2018年11月开始，爱心衣橱省级站点、爱心衣橱总部及爱心衣橱管委会将分多次对申请的学校进行初审、复审及终审；</p>
                                        <p>3、新衣定制：项目预计在2018年12月31日前开始根据筹款及学校审核结果定制新衣；</p>
                                        <p>4、发放反馈：自2019年1月开始，陆续发放新衣，2019年12月31日项目执行完毕同时在爱心衣橱官方网站及腾讯乐捐对外公示项目报告。</p>
                                        <h3>执行能力说明</h3>
                                        <p>2017年爱心衣橱心暖新衣项目在腾讯公益平台筹款共计支出3795993.45元。爱心衣橱心暖新衣项目自2011年6月6日正式立项，截至2018年7月，爱心衣橱心暖新衣项目已将防风防雨保暖透气的新衣服送到全国28个省，371个区县的1901所学校，共计200916个孩子穿上了这套温暖新衣。</p>
                                        <h3>关于我们</h3>
                                        <p>爱心衣橱是由多家主流媒体、多位主持人、编导、记者、企业家、名人明星共同参与推进的一项爱心工程。以“用爱心呵护孩子冷暖”为使命，通过各种渠道，汇聚社会各方力量筹集资金，为偏远、贫困地区的孩子们定制防风防雨保暖透气的新衣服，并倡议社会各界爱心人士关注贫困地区儿童的心灵成长和教育问题。2018年3月，爱心衣橱在中华社会救助基金会下设立爱心衣橱公益基金，主要项目有心暖新衣、鲲鹏助学、传爱项目和益童计划。</p>
                                        <img src={a_1} alt="a-1" />
                                        <h3>发票说明</h3>
                                        <p>基于人工、邮寄成本，中华社会救助基金会将为捐款金额100元（含）以上的乐捐网友开具个人捐赠票据。希望爱心用户按需申请。（需要捐赠收据的必须将交易单号、商户单号、QQ号、发票抬头、金额、捐赠渠道、捐赠日期、联系电话、地址、邮编等信息发至chenshuqiang@csaf.org.cn，经确认后我会会尽快回邮捐赠收据）。 </p>
                                        <Button size='large' type="primary" className='donation-btn'>我要捐款</Button>
                                    </TabPane>
                                    <TabPane className="project-timeline" tab="项目进展" key="2">
                                        <Timeline className="timeline">
                                            <Timeline.Item className="p-t-item">
                                                <div className="timeline-right">
                                                    <h3>七年送衣路,因你而温暖！</h3>
                                                    <p>2011年6月，爱心衣橱正式成立，到今天已经走过了整整七个春秋，全国28个省，448个区县的2350所学校，共计243411个孩子收到了爱心衣橱心暖新衣项目送去的温暖新衣。 <a className='detail-link' >查看详情>></a></p>
                                                    <div className="time">2018-11-22 15:21:55</div>
                                                </div>
                                            </Timeline.Item>
                                            <Timeline.Item className="p-t-item">
                                                <div className="timeline-right">
                                                    <p>【月捐上线】2018年10月11日，爱心衣橱心暖新衣项目“月捐”正式上线！一键操作，月月温暖！每月定期捐赠16.5元，坚持一年即可为一名山区孩子送去一件御寒冬衣。让公益成为一种生活方式，让爱的故事一直延续下去！下图附“月捐”开通指引及“月捐”福利~</p>
                                                    <div className="time">2018-10-16</div>
                                                </div>
                                            </Timeline.Item>
                                            <Timeline.Item className="p-t-item">
                                                <div className="timeline-right">
                                                    <h3>12800个乡村孩子在等待您的帮助</h3>
                                                    <p>就2018年上半年，爱心衣橱收到了来自全国600余所小学、近8万名乡村小学生的新衣申请。经过6、7、8三个月的审核，爱心衣橱筛选出了其中符合标准的近500所贫困乡村小学，5万余名乡村小学生将在今年冬天或是2019年春季穿上心暖新衣。<a className="detail-link">查看详情>></a></p>
                                                    <div className="time">2018-09-05 20:48:42</div>
                                                </div>
                                            </Timeline.Item>
                                            <Timeline.Item className="p-t-item">
                                                <div className="timeline-right">
                                                    <p>截至2018年7月，爱心衣橱心暖新衣项目已将防风防雨保暖透气的新衣服送到全国28个省，371个区县的1901所学校，共计200916个孩子。该项目原计划继续为一万名乡村学子筹集冬衣，2018年由于学校申请量增加，因此申请提高筹款目标为3600000元，同时申请项目延期。</p>
                                                    <div className="time">2018-08-15 10:02:43</div>
                                                </div>
                                            </Timeline.Item>
                                        </Timeline>
                                    </TabPane>
                                </Tabs>
                            </div>
                            <div className='bottom-right'>
                                <div className="weichat-qrccode">
                                    <div className="img"></div>
                                    <div className="body">
                                        <div className="title"><i className='icon'></i>微信支付</div>
                                        <div className="tip">扫一扫，微信支付即可捐款，公益更便捷！</div>
                                    </div>
                                </div>
                                <div className="project-organization">
                                    <div className="item">
                                        <div className="title">发起方</div>
                                        <div className="body">
                                        爱心衣橱
                                        </div>
                                    </div>
                                    <div className="item">
                                        <div className="title">执行方</div>
                                        <div className="body">爱心衣橱</div>
                                    </div>
                                    <div className="item">
                                        <div className="title">公募支持</div>
                                        <div className="body">
                                            中华社会救助基金会<br />(善款由该基金会接收)
                                        </div>
                                    </div>
                                    <div className="item">
                                        <div className="title">募捐方案备案编号</div>
                                        <div className="body"> 53100000500021326DA18051 </div>
                                    </div>
                                </div>
                                <div className="donation-feedback">
                                    <div className="title">你专属的爱心回馈</div>
                                    <div className="item love-icon">
                                        点亮爱心图标<br />永不熄灭的图标彰显爱心身份
                                    </div>
                                    <div className="item caibei-icon">
                                        奖励彩贝积分<br />10彩贝积分奖励持续鼓励
                                    </div>
                                    <div className="item bean-icon">
                                        公益豆奖励<br />没捐一元获得一个公益豆
                                    </div>
                                </div>
                                <div className="right-recommend">
                                    <div className="title"><span className="text">你可能会关注</span></div>
                                    <div className="project-list"></div>
                                </div>
                                <div className="frequently-problems">
                                        <div className="title">
                                            <span className="text">常见问题</span>
                                            <a href=".">意见反馈</a>
                                        </div>
                                        <div className="problem-list">
                                            <div className="item">
                                                <a href=".">1. 乐捐平台项目审核标准是什么？</a>
                                            </div>
                                            <div className="item">
                                                <a href=".">2. 哪些用户可以在乐捐平台上发起项目？</a>
                                            </div>
                                            <div className="item">
                                                <a href=".">3. 如何在乐捐平台上发起项目？</a>
                                            </div>
                                            <div className="item">
                                                <a href=".">4. 完成项目募捐后，如何进行拨款？</a>
                                            </div>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <Footer />
            </div>
        )
    }
}