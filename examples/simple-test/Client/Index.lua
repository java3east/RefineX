print("hello from client!")

Event.SubscribeRemote("test", function (...)
    print("event received", ...)
end)