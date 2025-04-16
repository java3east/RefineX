---@class test
---@field cb fun(test: test, simulation: simulation)
---@field name string
---@field context 'FIVEM'|'HELIX'
test = {}
test.__index = test

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
    return obj
end

function test:run()
    local t = self
    simulation:new(function(sim)
        t.cb(t, sim)
    end, self.context, "TEST:" .. self.name)
end
