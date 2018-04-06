package io.github.johnfg10

import io.github.johnfg10.groovy.GroovyDenyAllFilter
import io.github.johnfg10.groovy.GroovyShellManager
import io.github.johnfg10.helpers.createClient
import org.apache.commons.lang3.exception.ExceptionUtils
import org.kohsuke.groovy.sandbox.GroovyValueFilter
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

lateinit var client: IDiscordClient

fun main(args: Array<String>) {
    client = createClient("NDMxNDg1ODU0NzMzODI4MTA3.DafgXQ.QNsh9j-appUY9F5ZvdMf9qaxYxA", true)!!
    val dispatcher = client.dispatcher
    dispatcher.registerListener(ExpeditCore())
}

class ExpeditCore(){

    private val groovyShellManager = GroovyShellManager()

    init {
    }

    @EventSubscriber
    fun onReady(readyEvent: ReadyEvent){
        GroovyDenyAllFilter().register()
    }

    @EventSubscriber
    fun onMessageReceivedEvent(event: MessageReceivedEvent){
        val msg = event.message
        if (msg.content.startsWith("eval")){
            val msgContent = msg.content.replaceFirst("eval", "")
            println(msgContent)

            try{
                groovyShellManager.shell.setVariable("latestmessage", event)
                val eval = groovyShellManager.eval(msgContent).toString()
                println("eval : $eval")
                if (eval.isBlank()){
                    msg.reply("Sorry the code returned null/empty")
                }else{
                    msg.reply(eval)
                }
                //msg.reply(groovyShellManager.eval(msgContent).toString())
                //groovyEvalManager.binding.setVariable("message", event.message)
            }catch (e: Exception){
                msg.reply("An execution error occured \n ${e.message} \n ${ExceptionUtils.getStackTrace(e)}")
            }
        }
    }
}
