test.setResource('examples', 'simple-test')

test:new(function (test, sim)
    local client = sim:connect({ "test" })
    sim:doTicks(10)
end, 'RUN', 'HELIX')

test.runAll()