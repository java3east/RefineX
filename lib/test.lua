---@class test
---@field cb fun(test: test, simulation: simulation)
---@field name string
---@field context 'FIVEM'|'HELIX'
test = {}
test.__index = test

---@type table<test>
local tests = {}

local resource = {}

function test.setResource(path, name)
    resource.path = path
    resource.name = name
end

local simulations = {}
local mutate = false

function test.doMutations(m)
    mutate = m
end

function test.runAll()
    for _, test in ipairs(tests) do
        test:run()
    end
end

---Creates a new test object.
---@param cb fun(test: test, simulation: simulation)
---@param name string
---@param context 'FIVEM'|'HELIX'
---@return test test
function test:new(cb, name, context)
    local obj = {}
    setmetatable(obj, test)
    obj.name = name
    obj.cb = cb
    obj.context = context
    table.insert(tests, obj)
    return obj
end

function test:run()
    local t = self
    simulation:new(function(sim)
        table.insert(simulations, sim)
        sim:loadAndStart(resource.path, resource.name)
        t.cb(t, sim)
        sim:postProcess()
    end, self.context, "TEST:" .. self.name)
end
