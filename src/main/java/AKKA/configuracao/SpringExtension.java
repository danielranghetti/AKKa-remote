package AKKA.configuracao;

import akka.actor.Actor;
import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringExtension implements Extension {

    private ApplicationContext applicationContext;

    public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    Props props(Class<? extends Actor> actorBeansClass) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeansClass);
    }

    public Props props(Class<? extends Actor> actorBeansClass, Object... parameters) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeansClass, parameters);
    }

    public Props props(String actorBeanName) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
    }


}
