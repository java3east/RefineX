print("hello from server!")

Events.SubscribeRemote('test', function (source, num)
    if num == 1 then
        Events.CallRemote('test', source, "hello from server!")
    end
end)