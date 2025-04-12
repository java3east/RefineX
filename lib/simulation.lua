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
function client:new(simulation, identifier)
    local cl = {}
    setmetatable(cl, client)
    cl.simulation = simulation
    cl.identifier = identifier
    return cl
end

---Connects this client to the server
function client:connect()
    self.source = AddSimulatedClient(self.simulation.id, self.identifier)
end

function client:__tostring()
    return "client(" .. tostring(self.simulation) .. ", " .. tostring(self.source) .. ")"
end

---@class simulation
---@field id number
simulation = {}
simulation.__index = simulation

---Creates a new simulation
---@param cb fun(simulation: simulation)
---@param context 'FIVEM'|'HELIX'
function simulation:new(cb, context)
    Async(function()
        local sim = {}
        setmetatable(sim, simulation)
        sim.id = CreateSimulation(context)
        cb(sim)
    end)
end

---Connects a new client with the given identifiers
---@nodiscard
---@param identifier table<string>
---@return client client
function simulation:connect(identifier)
    local cl = client:new(self, identifier)
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
function simulation:start(name)
    StartResource(self.id, name)
end

---Loads and starts the resource with the given name in the given containing folder
---Only works if the defined name in the manifest is the same as the folder name
---@param path string the containing folder
---@param name string the name of the resource
function simulation:loadAndStart(path, name)
    self:load(path .. "/" .. name)
    self:start(name)
end

---Runs the given function asynchronously
---@param fun fun() the function to run
function simulation.async(fun)
    Async(fun)
end

function simulation:__tostring()
    return "simulation(" .. tostring(self.id) .. ")"
end