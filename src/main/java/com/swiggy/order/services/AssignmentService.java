package com.swiggy.order.services;

import com.swiggy.order.enums.City;
import fulfillment.FulFillmentGrpc;
import fulfillment.Fulfillment;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;

import java.util.concurrent.TimeUnit;

public class AssignmentService {
    private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
            .usePlaintext()
            .build();;
    private static final FulFillmentGrpc.FulFillmentBlockingStub blockingStub = FulFillmentGrpc.newBlockingStub(channel);


    public static String assignAgent(Long id, City city) {
        Fulfillment.AssignRequest request = Fulfillment.AssignRequest.newBuilder()
                .setOrderId(id)
                .setCity(city.name())
                .build();
        Fulfillment.AssignResponse response = blockingStub.assignDeliveryAgent(request);
        return response.getMessage();
    }

    @PreDestroy
    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
