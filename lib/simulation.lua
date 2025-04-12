---@class client
client = {}
client.source = -1
client.__index = client

function client:new(simulation, identifier)
    local cl = {}
    setmetatable(cl, client)
    cl.simulation = simulation
    cl.identifier = identifier
    return cl
end

function client:connect()
    self.source = AddSimulatedClient(self.simulation.id, self.identifier)
end

function client:__tostring()
    return "client(" .. tostring(self.simulation) .. ", " .. tostring(self.source) .. ")"
end

---@class simulation
simulation = {}
simulation.__index = simulation

function simulation:new()
    local sim = {}
    setmetatable(sim, simulation)
    sim.id = CreateSimulation()
    return sim
end

function simulation:connect(identifier)
    local cl = client:new(self, identifier)
    cl:connect()
    return cl
end

function simulation.async(fun)
    Async(fun)
end

function simulation:__tostring()
    return "simulation(" .. tostring(self.id) .. ")"
end