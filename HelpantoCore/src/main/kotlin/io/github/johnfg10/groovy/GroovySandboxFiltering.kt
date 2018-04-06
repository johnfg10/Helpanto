package io.github.johnfg10.groovy

import groovy.lang.Script
import org.kohsuke.groovy.sandbox.GroovyValueFilter
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.Message
import sx.blah.discord.handle.obj.IMessage
import java.io.InvalidClassException
import java.math.BigDecimal
import java.math.BigInteger


class GroovySandboxFiltering : GroovyValueFilter() {
    override fun filter(o: Any?): Any {
        if (o != null) {
            val className = o::class.simpleName
            if (className != null){
                println(!o::class.simpleName!!.matches(Regex("Script\\d")))
                if(!o::class.simpleName!!.matches(Regex("Script\\d")) || o is Script ){
                    if (allowedClasses.contains(o::class)){
                        return o
                    }
                    if (o::class.simpleName!!.matches(Regex("Script\\d"))){
                        return o
                    }
                    println("Name: " + o::class.simpleName)
                    throw SecurityException("Access to this part of the API is not allowed by the sandbox, if you believe this is in error please make a report on github!")
                }else{
                    println("test")
                    return o;
                }
            }else{
                throw InvalidClassException("The class had not simple name attached")
            }
        }
        return Any()
    }

    companion object {
        val allowedClasses = listOf(
                String::class, Int::class, Double::class,
                Float::class, BigInteger::class, BigDecimal::class,
                IMessage::class, Message::class,
                mapOf<Any?, Any?>()::class, Script::class, MessageReceivedEvent::class
        )
    }
}