simulation:new(function(sim)
    local client = sim:connect({ "test" })
    sim:loadAndStart('examples', 'simple-test')
end, 'HELIX')
