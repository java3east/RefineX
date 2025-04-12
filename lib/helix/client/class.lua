---@class class
---@field protected __name string
Class = {}
Class.__index = Class

---Creates a new class
---@generic T
---@param name `T`
---@return T
function Class:new(name, parents)
    local obj = {}
    obj.__parents = parents or {}
    setmetatable(obj, {
        __index = function(t, k)
            local value = rawget(t, k)
            if value then return value end
            for _, parent in ipairs(t.__parents) do
                value = parent[k]
                if value then
                    rawset(t, k, value)
                    return value
                end
            end
        end,
        __call = function(cls, ...)
            return cls:new(...)
        end,
    })
    self.__index = self
    obj.__name = name
    return obj
end
