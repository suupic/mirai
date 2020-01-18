package net.mamoe.mirai.japt

import kotlinx.coroutines.runBlocking
import kotlinx.io.core.ByteReadPacket
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotAccount
import net.mamoe.mirai.contact.GroupInternalId
import net.mamoe.mirai.data.AddFriendResult
import net.mamoe.mirai.data.ImageLink
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.network.BotNetworkHandler
import net.mamoe.mirai.utils.MiraiInternalAPI
import net.mamoe.mirai.utils.MiraiLogger
import net.mamoe.mirai.utils.toList

internal class BlockingBotImpl(private val bot: Bot) : BlockingBot {
    @MiraiInternalAPI
    override fun getAccount(): BotAccount = bot.account

    override fun getUin(): Long = bot.uin
    override fun getLogger(): MiraiLogger = bot.logger
    @UseExperimental(MiraiInternalAPI::class)
    override fun getQQs(): List<BlockingQQ> = bot.qqs.delegate.toList().map { it.blocking() }

    override fun getQQ(id: Long): BlockingQQ = bot.getQQ(id).blocking()
    @UseExperimental(MiraiInternalAPI::class)
    override fun getGroups(): List<BlockingGroup> = bot.groups.delegate.toList().map { it.blocking() }

    override fun getGroup(id: Long): BlockingGroup = runBlocking { bot.getGroup(id).blocking() }
    override fun getGroupByInternalId(internalId: Long): BlockingGroup = runBlocking { bot.getGroup(GroupInternalId(internalId)).blocking() }
    override fun getNetwork(): BotNetworkHandler = bot.network
    override fun login() = runBlocking { bot.login() }
    override fun getLink(image: Image): ImageLink = bot.run { runBlocking { image.getLink() } }
    override fun downloadAsByteArray(image: Image): ByteArray = bot.run { runBlocking { image.downloadAsByteArray() } }
    override fun download(image: Image): ByteReadPacket = bot.run { runBlocking { image.download() } }
    override fun addFriend(id: Long, message: String?, remark: String?): AddFriendResult = runBlocking { bot.addFriend(id, message, remark) }
    override fun approveFriendAddRequest(id: Long, remark: String?) = runBlocking { bot.approveFriendAddRequest(id, remark) }
    override fun dispose(throwable: Throwable?) = bot.dispose(throwable)
}