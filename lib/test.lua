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

function test.runAll(mutation)
    local hasIssues = false
    for _, test in ipairs(tests) do
        if test:run(mutation) then
            hasIssues = true
        end
    end
    if mutation ~= 0 and not hasIssues then
        REFX_ERROR("ERROR", "All tests passed, mutation survived! (replaced '==' with '~=')", "write tests covering the mutation", {{file = "", line = -1}})
    else
        print("ALL MUTATIONS KILLED => PASSED")
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

function test:run(mutation)
    mutation = mutation or 0
    local sim = simulation:new('HELIX', "TEST:" .. self.name .. "/MUTATION:" .. tostring(mutation))
    sim:loadAndStart(resource.path, resource.name, mutation)
    self:cb(sim)
    sim:postProcess()
    return sim:hasErrors()
end
