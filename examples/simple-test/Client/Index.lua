print("hello from client!")

Events.Subscribe("test", function (msg)
    print("server said", msg)
end)

Events.Subscribe("tick", function (deltaTime)
    print("tick", deltaTime)
end)

x()

Events.CallRemote('test')
