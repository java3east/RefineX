test.setMarkersEnabled(true)

test:new(function (test, sim)
    local client = sim:connect({ "test" })
    sim:loadAndStart('examples', 'simple-test')
    sim:doTicks(10)
end, 'RUN', 'HELIX'):run()