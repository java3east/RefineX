print("hello from server!")

Events.SubscribeRemote('test', function (source)
    print("client is saying hello!")
    Events.CallRemote('test', source, "hello from server!")
end)