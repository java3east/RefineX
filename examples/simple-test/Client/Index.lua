print("hello from client!")

local function tick(deltaTime)

end

Events.SubscribeRemote("test", function (msg)
    print("server said", msg)
end)

Events.Subscribe("tick", tick)

Events.CallRemote('test', 1)