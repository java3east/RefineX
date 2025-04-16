print("hello from client!")

Events.SubscribeRemote("test", function (msg)
    print("server said", msg)
end)

Events.Subscribe("tick", function (deltaTime)
    print("tick", deltaTime)
end)

Events.CallRemote('test')
