package io.github.johnfg10.helpers

import sx.blah.discord.util.DiscordException
import org.eclipse.jetty.websocket.common.events.annotated.InvalidSignatureException.build
import sun.security.jgss.GSSUtil.login
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient

/**
 * creates a IDiscordClient from a token and either logs them in or returns a unlogged in instance
 */
fun createClient(token: String, login: Boolean): IDiscordClient? { // Returns a new instance of the Discord client
    val clientBuilder = ClientBuilder() // Creates the ClientBuilder instance
    clientBuilder.withToken(token) // Adds the login info to the builder
    try {
        return if (login) {
            clientBuilder.login() // Creates the client instance and logs the client in
        } else {
            clientBuilder.build() // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
        }
    } catch (e: DiscordException) { // This is thrown if there was a problem building the client
        e.printStackTrace()
        return null
    }
}