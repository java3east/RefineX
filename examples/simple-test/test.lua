test.setResource('examples', 'simple-test')

test:new(function (test, sim)
    local client = sim:connect({ "test" })
    sim:doTicks(10)
end, 'RUN', 'HELIX')

test:new(function (test, sim)
    local client = sim:connect({ "test" })
    sim:doTicks(10)
end, 'RUN2', 'HELIX')

test.runAll(0)
test.runAll(1)