package groovyx.gpars.samples.remote.chat

import groovyx.gpars.actor.Actors
import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.remote.RemoteActors

class ChatClientActor extends DefaultActor {
    String name
    def serverFuture

    def consoleActor = Actors.actor {
        loop {
            react { ChatMessage message ->
                println "${message.sender}: ${message.message}"
            }
        }
    }

    ChatClientActor(String host, int port, String name) {
        this.name = name
        serverFuture = RemoteActors.get(host, port, "chat-server")
    }

    public afterStop(List<Object> messages) {
        def server = serverFuture.get()
        server << new ChatMessage(action: "unregister", sender: name)
    }

    @Override
    protected void act() {
        def server = serverFuture.get()

        // register
        server << new ChatMessage(action: "register", sender: name, message: consoleActor)

        loop {
            react { line ->
                if (line == "@show") {
                    server << new ChatMessage(action: "show", sender: name)
                }
                else {
                    server << new ChatMessage(action: "say", sender: name, message: line)
                }
            }
        }
    }
}
