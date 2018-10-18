# Test tracing using Spring Cloud GCP in Kubernetes

### Set Docker image name
This should be deployable in an existing cluster by just updating `jib.to.image` in the `build.gradle` and the image in the `deployment.yml` to a repo you have access to.

### Deploy the application to the cluster

```bash
./deployAll.sh
```

### Call the app so spans are reported

The app exposes an endpoint `GET <external-ip>/`.  Send a request to this endpoint so that a span is reported to Stackdriver.

## Exception logged when trace is published

```
2018-10-18 17:49:28.396 DEBUG [-,,,] 1 --- [r-stackdriver}}] z.r.AsyncReporter$BoundedAsyncReporter   : Dropped 1 spans due to StatusRuntimeException(INTERNAL: Panic! This is a bug!)
```

```
io.grpc.StatusRuntimeException: INTERNAL: Panic! This is a bug!
	at io.grpc.Status.asRuntimeException(Status.java:526) ~[grpc-core-1.14.0.jar:1.14.0]
	at zipkin2.reporter.stackdriver.internal.AwaitableUnaryClientCallListener.onClose(AwaitableUnaryClientCallListener.java:89) ~[zipkin-sender-stackdriver-0.6.4.jar:na]
	at io.grpc.PartialForwardingClientCallListener.onClose(PartialForwardingClientCallListener.java:39) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.ForwardingClientCallListener.onClose(ForwardingClientCallListener.java:23) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.ForwardingClientCallListener$SimpleForwardingClientCallListener.onClose(ForwardingClientCallListener.java:40) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.CensusStatsModule$StatsClientInterceptor$1$1.onClose(CensusStatsModule.java:684) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.PartialForwardingClientCallListener.onClose(PartialForwardingClientCallListener.java:39) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.ForwardingClientCallListener.onClose(ForwardingClientCallListener.java:23) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.ForwardingClientCallListener$SimpleForwardingClientCallListener.onClose(ForwardingClientCallListener.java:40) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.CensusTracingModule$TracingClientInterceptor$1$1.onClose(CensusTracingModule.java:403) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ClientCallImpl.closeObserver(ClientCallImpl.java:459) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ClientCallImpl.access$300(ClientCallImpl.java:63) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl.close(ClientCallImpl.java:546) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl.access$600(ClientCallImpl.java:467) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ClientCallImpl$ClientStreamListenerImpl$1StreamClosed.runInContext(ClientCallImpl.java:584) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ContextRunnable.run(ContextRunnable.java:37) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.SerializingExecutor.run(SerializingExecutor.java:123) ~[grpc-core-1.14.0.jar:1.14.0]
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511) ~[na:1.8.0_181]
	at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[na:1.8.0_181]
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180) ~[na:1.8.0_181]
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293) ~[na:1.8.0_181]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[na:1.8.0_181]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[na:1.8.0_181]
	at java.lang.Thread.run(Thread.java:748) ~[na:1.8.0_181]
Caused by: java.lang.AbstractMethodError: io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder$NettyTransportFactory.newClientTransport(Ljava/net/SocketAddress;Lio/grpc/internal/ClientTransportFactory$ClientTransportOptions;)Lio/grpc/internal/ConnectionClientTransport;
	at io.grpc.internal.CallCredentialsApplyingTransportFactory.newClientTransport(CallCredentialsApplyingTransportFactory.java:47) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.InternalSubchannel.startNewTransport(InternalSubchannel.java:238) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.InternalSubchannel.obtainActiveTransport(InternalSubchannel.java:206) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ManagedChannelImpl$SubchannelImpl.requestConnection(ManagedChannelImpl.java:1417) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.PickFirstBalancerFactory$PickFirstBalancer.handleResolvedAddressGroups(PickFirstBalancerFactory.java:74) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.AutoConfiguredLoadBalancerFactory$AutoConfiguredLoadBalancer.handleResolvedAddressGroups(AutoConfiguredLoadBalancerFactory.java:106) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ManagedChannelImpl$NameResolverListenerImpl$1NamesResolved.run(ManagedChannelImpl.java:1286) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ChannelExecutor.drain(ChannelExecutor.java:73) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ManagedChannelImpl$LbHelperImpl.runSerialized(ManagedChannelImpl.java:1230) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.ManagedChannelImpl$NameResolverListenerImpl.onAddresses(ManagedChannelImpl.java:1290) ~[grpc-core-1.14.0.jar:1.14.0]
	at io.grpc.internal.DnsNameResolver$1.run(DnsNameResolver.java:251) ~[grpc-core-1.14.0.jar:1.14.0]
```