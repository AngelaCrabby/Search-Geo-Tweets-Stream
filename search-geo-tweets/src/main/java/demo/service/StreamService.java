package demo.service;

import org.springframework.ui.Model;

public interface StreamService {
    Model streamApi(Model model, int time) throws InterruptedException;
}
