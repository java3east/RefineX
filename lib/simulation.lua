---@class client
---@field simulation simulation
---@field identifier table<string>
client = {}
client.source = -1
client.__index = client

---Creates a new client object
---@nodiscard
---@param simulation simulation
---@param identifier table<string>
---@return client client
function client:new(simulation, identifier, mut)
    local cl = {}
    setmetatable(cl, client)
    cl.simulation = simulation
    cl.identifier = identifier
    cl.mut = mut
    return cl
end

---Connects this client to the server
function client:connect()
    self.source = AddSimulatedClient(self.simulation.id, self.identifier, self.mut)
end

function client:__tostring()
    return "client(" .. tostring(self.simulation) .. ", " .. tostring(self.source) .. ")"
end

---@class simulation
---@field id number
---@field mut number
simulation = {}
simulation.__index = simulation

---Creates a new simulation
---@param context 'FIVEM'|'HELIX'
---@param name string the name of the simulation
function simulation:new(context, name)
    local sim = {}
    setmetatable(sim, simulation)
    sim.id = CreateSimulation(context, name)
    return sim
end

---Connects a new client with the given identifiers
---@nodiscard
---@param identifier table<string>
---@return client client
function simulation:connect(identifier)
    local cl = client:new(self, identifier, self.mut)
    cl:connect()
    return cl
end

---Loads the given resource
---@param path string the path to the resource
function simulation:load(path)
    LoadResource(self.id, path)
end

---Starts the resource with the given name
---@param name string the name of the resource
---@param mutation number?
function simulation:start(name, mutation)
    mutation = mutation or 0
    self.mut = mutation
    StartResource(self.id, name, mutation)
end

---Loads and starts the resource with the given name in the given containing folder
---Only works if the defined name in the manifest is the same as the folder name
---@param path string the containing folder
---@param name string the name of the resource
---@param mutation number?
function simulation:loadAndStart(path, name, mutation)
    self:load(path .. "/" .. name)
    self:start(name, mutation)
end

---Simulates a tick for the simulation
---@param deltaTime number? the time in seconds since the last tick (default: 0.01 (10ms))
function simulation:tick(deltaTime)
    deltaTime = deltaTime or 0.01
    REFX_TICK(self.id, deltaTime)
end

---Performs the given amount of ticks with the given amount of delta
---@param count number? (default: 1) the amount of ticks to perform
---@param deltaTime number? (default: 0.01 (10ms))
function simulation:doTicks(count, deltaTime)
    count = count or 1
    for i = 1, count do
        self:tick(deltaTime)
    end
end

function simulation:hasErrors()
    return RefxSimHasErrors(self.id)
end

function simulation:postProcess()
    RefxSimPostProcess(self.id)
end

---Runs the given function asynchronously
---@param fun fun() the function to run
function simulation.async(fun)
    Async(fun)
end

function simulation:__tostring()
    return "simulation(" .. tostring(self.id) .. ")"
end