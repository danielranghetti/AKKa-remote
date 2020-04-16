package AKKA.pong.cong;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
   private final Class<? extends Actor> actorBeanName;



    public SpringActorProducer(ApplicationContext applicationContext, Class<? extends Actor> actorBeanName) {
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
    }

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(actorBeanName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(String.valueOf(actorBeanName));
    }
}
