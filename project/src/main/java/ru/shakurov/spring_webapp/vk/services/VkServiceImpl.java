package ru.shakurov.spring_webapp.vk.services;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.models.*;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.vk.authentication.VkAuthentication;
import ru.shakurov.spring_webapp.vk.handler.VkAuthenticationHandler;

import java.util.List;
import java.util.Optional;

@Service
public class VkServiceImpl implements VkService {
    @Value("${vk.app.id}")
    private String appId;
    @Value("${vk.app.client.secret}")
    private String clientSecret;
    @Value("${vk.redirect.url}")
    private String redirectUrl;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MoneyStorageRepository moneyStorageRepository;
    @Autowired
    private VkAuthenticationHandler vkAuthenticationHandler;

    @Override
    @Transactional
    public void authenticateWithVkCode(String code) throws ClientException, ApiException {
        System.out.println(appId);
        System.out.println(clientSecret);
        System.out.println(redirectUrl);
        System.out.println(code);
        System.out.println();
        System.out.println();
        System.out.println();


        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Integer.parseInt(appId), clientSecret, redirectUrl, code)
                .execute();

        System.out.println(authResponse.getAccessToken());
        System.out.println(authResponse.getEmail());
        System.out.println(authResponse.getUserId());


        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        //получаю всю информацию о пользователе с конкретным Id
        List<UserXtrCounters> userXtrCountersList = vk.users().get(actor).userIds(actor.getId().toString()).execute();
        if (userXtrCountersList.get(0) != null) {
            UserXtrCounters userXtrCounters = userXtrCountersList.get(0);
            Optional<User> userOptional = userRepository.findVkUserByEmail(authResponse.getEmail());
            User user;
            if (userOptional.isPresent()) {
                user = userOptional.get();
            } else {
                user = User.builder()
                        .alias("vkId" + userXtrCounters.getId())
                        .name(userXtrCounters.getFirstName() + " " + userXtrCounters.getLastName())
                        .email(authResponse.getEmail())
                        .state(State.VK)
                        .status(Status.ACTIVE)
                        .role(Role.USER)
                        .build();
                userRepository.save(user);
                MoneyStorage storage = MoneyStorage.builder()
                        .balance(0L)
                        .user(user)
                        .build();
                moneyStorageRepository.save(storage);
            }
            springAuth(actor.getAccessToken(), user);
        }
    }

    private void springAuth(String vkToken, User user) {
        VkAuthentication vkAuthentication = vkAuthenticationHandler.creatVkAuthentication(vkToken, user);
        vkAuthenticationHandler.addVkAuthenticationToSpring(vkAuthentication);
    }
}
