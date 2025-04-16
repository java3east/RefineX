function REFX_TICK(deltaTime)
    REFX_DISPATCH_EVENT({
        name = "tick",
        isNet = false,
        source = 0,
        destination = 0,
        data = {deltaTime}
    }, true)
    REFX_EV_MAN_TICK()
end