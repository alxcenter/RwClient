package io.bot.telega.steps.start;

import io.bot.model.Monitoring;
import io.bot.model.User;
import io.bot.service.MonitoringService;
import io.bot.telega.steps.Stepper;
import io.bot.telega.steps.UpdateResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Scope("prototype")
public class StartAction implements UpdateResolver {
    @Autowired
    private CheckAuthStep checkAuthStep;
    @Autowired
    private ChoseStationStep choseStationStep;
    @Autowired
    private ChoseTrainStep choseTrainStep;
    @Autowired
    private ChosePlaceStep chosePlaceStep;
    @Autowired
    private ChosePassengersStep chosePassengersStep;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MonitoringService monitoringService;
    private User user;
    private boolean isUserExist;


    private Monitoring monitoring;
    private Stepper stepper;
    private int progress = 0;


    @Override
    public void updateAction(Update update) {
        checkForStart(update);
        switch (progress) {
            case 0:
                if (isUserExist) {
                    progress = 1;
                    stepper = choseStationStep;
                } else {
                    progress = getStep().setMonitoring(getMonitoring()).updateAction(update) ? 1 : progress;
                    if (progress == 1) {
                        isUserExist = true;
                        user = monitoring.getRelatesTo();
                    }
                    stepper = progress == 1 ? choseStationStep.setMonitoring(monitoring) : stepper;
                }
                if (progress != 1) {
                    break;
                }
            case 1:
                progress = getStep().setMonitoring(getMonitoring()).updateAction(update) ? 2 : progress;
                stepper = progress == 2 ? choseTrainStep.setMonitoring(monitoring) : stepper;
                if (progress != 2) {
                    break;
                }
            case 2:
                progress = getStep().updateAction(update) ? 3 : progress;
                stepper = progress == 3 ? chosePlaceStep.setMonitoring(monitoring) : stepper;
                if (progress != 3) {
                    break;
                }
            case 3:
                progress = getStep().updateAction(update) ? 4 : progress;
                stepper = progress == 4 ? chosePassengersStep.setMonitoring(monitoring) : stepper;
                if (progress != 4) {
                    break;
                }
            case 4:
                progress = getStep().updateAction(update) ? 5 : progress;
                if (progress != 5) {
                    break;
                }
            case 5:
                monitoringService.createMonitoring(monitoring);
                resetAll();
                break;
            default:
                break;
        }
    }

    private Stepper getStep() {
        return stepper == null ? stepper = checkAuthStep : stepper;
    }

    private Monitoring getMonitoring() {
        if (monitoring == null) {
            monitoring = new Monitoring();
            monitoring.setRelatesTo(user);
        }
        return monitoring;
    }

    private void checkForStart(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
            resetAll();
        }
    }

    private void resetAll() {
        stepper = null;
        monitoring = null;
        progress = 0;
        checkAuthStep = applicationContext.getBean(CheckAuthStep.class);
        choseStationStep = applicationContext.getBean(ChoseStationStep.class);
        choseTrainStep = applicationContext.getBean(ChoseTrainStep.class);
        chosePlaceStep = applicationContext.getBean(ChosePlaceStep.class);
        chosePassengersStep = applicationContext.getBean(ChosePassengersStep.class);
    }

}
