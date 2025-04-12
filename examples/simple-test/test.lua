simulation:new(function(sim)
    local client = sim:connect({ "test" })
    sim:load('examples/simple-test/')
end, 'HELIX')
