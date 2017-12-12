package com.fire.zhihudaily.entity

import java.io.Serializable

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
data class LatestNews(
        var date: String,// 20171208
        var stories: List<Story>,
        var top_stories: List<Top_story>
) : Serializable


data class InternetNews(
		var stories: List<Story>,
		var description: String,// 把黑客知识科普到你的面前
		var background: String,// http://p4.zhimg.com/32/55/32557676e84fcfda4d82d9b8042464e1.jpg
		var color: Int,// 9699556
		var name: String,// 互联网安全
		var image: String,// https://pic1.zhimg.com/306f3ab291c415f40fe4485b75627230.jpg
		var editors: List<Editor>,
		var image_source: String//
) : Serializable

data class Editor(
		var url: String,// http://www.zhihu.com/people/THANKS
		var bio: String,// FreeBuf.com 小编，专注黑客与极客
		var id: Int,// 65
		var avatar: String,// http://pic4.zhimg.com/ecd93e213_m.jpg
		var name: String// THANKS
) : Serializable

data class Section(
		var thumbnail: String,// https://pic4.zhimg.com/v2-de6959fc0923aa6f1a0853553f7780bf.jpg
		var name: String,// 大误
		var id: Int// 29
) : Serializable

data class NewsDetail(
		var body: String,//
		var image_source: String,// 编辑瞎说的
		var title: String,// 大误 · 拉小提琴这么不容易啊
		var image: String,// https://pic4.zhimg.com/v2-533ca3e05b3cab2a9470778d7a4527f7.jpg
		var share_url: String,// http://daily.zhihu.com/story/9660285
		var js: List<String>,
		var id: Int,// 9660285
		var ga_prefix: String,// 120912
		var images: List<String>,
		var type: Int,// 0
		var section: Section,
		var css: List<String>,
		var theme: Theme,
		var recommenders: List<Recommenders>
) : Serializable


data class Theme(
		var thumbnail: String,// http://pic4.zhimg.com/2c38a96e84b5cc8331a901920a87ea71.jpg
		var name: String,// 用户推荐日报
		var id: Int// 12
) : Serializable


data class Recommenders(
		var avatar: String// http://pic1.zhimg.com/e70b91873695eb59e7d9a145f87a1688_m.jpg
) : Serializable


data class CommentsCount(
		var long_comments: Int,// 117
		var popularity: Int,// 4348
		var short_comments: Int,// 440
		var comments: Int// 557
) : Serializable


data class LisSections(
		var timestamp: Int,// 1459569600
		var stories: List<Story>,
		var name: String// 大误
) : Serializable

data class Story(
        var images: List<String>,
        var type: Int,// 0
        var id: Int,// 9660338
        var display_date: String,// 5 月 22 日
        var date: String,// 20160522
        var ga_prefix: String,// 120816
        var title: String// 只能挨冻，或者吸霾？聊到北方供暖，也许还有别的解决办法
) : Serializable

data class Top_story(
		var image: String,// https://pic2.zhimg.com/v2-b73be22329a81f478da264a92c5964ed.jpg
		var type: Int,// 0
		var id: Int,// 9660338
		var ga_prefix: String,// 120816
		var title: String// 只能挨冻，或者吸霾？聊到北方供暖，也许还有别的解决办法
) : Serializable

data class ShortComments(
		var comments: List<Comment>
) : Serializable

data class Comment(
		var author: String,// Ryuuzt
		var content: String,// 有些人就是嘴炮而已。还有些人自甘轻贱自己，自己看不起自己的职业。
		var avatar: String,// http://pic1.zhimg.com/da8e974dc_im.jpg
		var time: Int,// 1512974696
		var reply_to: Reply_to,
		var id: Int,// 30807290
		var likes: Int// 0
)

data class Reply_to(
		var content: String,// 这里我要打抱不平了，为什么服务者就一定要低人一等呢？难道平等的观念就只是网友嘴上说说的？你说的老板和秘书关系的核心不是雇佣关系吗？有礼貌是每个人都应该有的素质，服务者还需要职业修养，但上面没有哪项是要求低人一等。消费者花钱就可以为所欲为？花钱就可以随便秀低素质？
		var status: Int,// 0
		var id: Int,// 30805634
		var author: String// 鱼线上的蚯蚓
)
