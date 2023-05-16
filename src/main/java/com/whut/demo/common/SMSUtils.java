package com.whut.demo.common;


import com.aliyun.sdk.service.dysmsapi20170525.models.*;



import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;

import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.aliyun.sdk.service.dysmsapi20170525.*;

import darabonba.core.client.ClientOverrideConfiguration;



import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SMSUtils {
    public static void sendMessage(String phone){
    StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
            .accessKeyId("/////")
            .accessKeySecret("/////")
            //.securityToken("<your-token>") // use STS token
            .build());

    // Configure the Client
    AsyncClient client = AsyncClient.builder()
            .region("cn-hangzhou") // Region ID
            //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
            .credentialsProvider(provider)
            //.serviceConfiguration(Configuration.create()) // Service-level configuration
            // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
            .overrideConfiguration(
                    ClientOverrideConfiguration.create()
                            .setEndpointOverride("dysmsapi.aliyuncs.com")
                    //.setConnectTimeout(Duration.ofSeconds(30))
            )
            .build();

    // Parameter settings for API request

            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
            .signName("武理快递")
            .templateCode("SMS_275345458")
            .phoneNumbers(phone)
            .templateParam("{\"code\":\"1234\"}")
            // Request-level configuration rewrite, can set Http request parameters, etc.
            // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
            .build();

    // Asynchronously get the return value of the API request
    CompletableFuture<com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse> response = client.sendSms(sendSmsRequest);
    // Synchronously get the return value of the API request
    SendSmsResponse resp;

    {
        try {
            resp = response.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
//        System.out.println(new Gson().toJson(resp));
    // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

    // Finally, close the client
//        client.close();

}}
