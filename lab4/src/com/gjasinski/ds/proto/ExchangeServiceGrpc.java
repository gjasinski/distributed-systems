package com.gjasinski.ds.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: ExchangeServiceInterface.proto")
public final class ExchangeServiceGrpc {

  private ExchangeServiceGrpc() {}

  public static final String SERVICE_NAME = "ExchangeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetExchangeStatusStreamMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.gjasinski.ds.proto.CurrencyCollection,
      com.gjasinski.ds.proto.ExchangeStatus> METHOD_GET_EXCHANGE_STATUS_STREAM = getGetExchangeStatusStreamMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.gjasinski.ds.proto.CurrencyCollection,
      com.gjasinski.ds.proto.ExchangeStatus> getGetExchangeStatusStreamMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.gjasinski.ds.proto.CurrencyCollection,
      com.gjasinski.ds.proto.ExchangeStatus> getGetExchangeStatusStreamMethod() {
    return getGetExchangeStatusStreamMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.gjasinski.ds.proto.CurrencyCollection,
      com.gjasinski.ds.proto.ExchangeStatus> getGetExchangeStatusStreamMethodHelper() {
    io.grpc.MethodDescriptor<com.gjasinski.ds.proto.CurrencyCollection, com.gjasinski.ds.proto.ExchangeStatus> getGetExchangeStatusStreamMethod;
    if ((getGetExchangeStatusStreamMethod = ExchangeServiceGrpc.getGetExchangeStatusStreamMethod) == null) {
      synchronized (ExchangeServiceGrpc.class) {
        if ((getGetExchangeStatusStreamMethod = ExchangeServiceGrpc.getGetExchangeStatusStreamMethod) == null) {
          ExchangeServiceGrpc.getGetExchangeStatusStreamMethod = getGetExchangeStatusStreamMethod = 
              io.grpc.MethodDescriptor.<com.gjasinski.ds.proto.CurrencyCollection, com.gjasinski.ds.proto.ExchangeStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "ExchangeService", "GetExchangeStatusStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.gjasinski.ds.proto.CurrencyCollection.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.gjasinski.ds.proto.ExchangeStatus.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getGetExchangeStatusStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExchangeServiceStub newStub(io.grpc.Channel channel) {
    return new ExchangeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExchangeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExchangeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExchangeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExchangeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ExchangeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getExchangeStatusStream(com.gjasinski.ds.proto.CurrencyCollection request,
        io.grpc.stub.StreamObserver<com.gjasinski.ds.proto.ExchangeStatus> responseObserver) {
      asyncUnimplementedUnaryCall(getGetExchangeStatusStreamMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetExchangeStatusStreamMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.gjasinski.ds.proto.CurrencyCollection,
                com.gjasinski.ds.proto.ExchangeStatus>(
                  this, METHODID_GET_EXCHANGE_STATUS_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class ExchangeServiceStub extends io.grpc.stub.AbstractStub<ExchangeServiceStub> {
    private ExchangeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeServiceStub(channel, callOptions);
    }

    /**
     */
    public void getExchangeStatusStream(com.gjasinski.ds.proto.CurrencyCollection request,
        io.grpc.stub.StreamObserver<com.gjasinski.ds.proto.ExchangeStatus> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetExchangeStatusStreamMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ExchangeServiceBlockingStub extends io.grpc.stub.AbstractStub<ExchangeServiceBlockingStub> {
    private ExchangeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.gjasinski.ds.proto.ExchangeStatus> getExchangeStatusStream(
        com.gjasinski.ds.proto.CurrencyCollection request) {
      return blockingServerStreamingCall(
          getChannel(), getGetExchangeStatusStreamMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExchangeServiceFutureStub extends io.grpc.stub.AbstractStub<ExchangeServiceFutureStub> {
    private ExchangeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_EXCHANGE_STATUS_STREAM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExchangeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExchangeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EXCHANGE_STATUS_STREAM:
          serviceImpl.getExchangeStatusStream((com.gjasinski.ds.proto.CurrencyCollection) request,
              (io.grpc.stub.StreamObserver<com.gjasinski.ds.proto.ExchangeStatus>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ExchangeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getGetExchangeStatusStreamMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
