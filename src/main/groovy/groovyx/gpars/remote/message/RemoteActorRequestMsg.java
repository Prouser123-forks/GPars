package groovyx.gpars.remote.message;

import groovyx.gpars.actor.Actor;
import groovyx.gpars.remote.RemoteConnection;
import groovyx.gpars.remote.RemoteHost;
import groovyx.gpars.serial.SerialMsg;

import java.util.UUID;

public class RemoteActorRequestMsg extends SerialMsg {
    private final String actorName;

    public RemoteActorRequestMsg(UUID hostId, String actorName) {
        super(hostId);
        this.actorName = actorName;
    }

    @Override
    public void execute(RemoteConnection conn) {
        updateRemoteHost(conn);

        Actor actor = conn.getLocalHost().get(Actor.class, actorName);
        conn.getHost().write(new RemoteActorReplyMsg(actorName, actor));
    }
}
