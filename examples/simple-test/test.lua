test:new(function (test, sim)
    local client = sim:connect({ "test" })
    sim:loadAndStart('examples', 'simple-test')
end, 'RUN', 'HELIX'):run()
