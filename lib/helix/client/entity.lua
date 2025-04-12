---@class Entity : class
---@field private localId number
---@field private netId number
Entity = Class:new("Entity", {})

---Creates a new entity
---@param localId number
---@param netId number
function Entity:new(localId, netId)
    self.localId = localId
    self.netId = netId
end

function Entity.GetAll()
    local data = GetAllEntities()
    local entities = {}

    for _, d in ipairs(data) do
        local ent = Entity(d.id)
        table.insert(entities, ent)
    end

    return entities
end

---Returns the network id of the entity.
---@nodiscard
---@return number the netowrk id
function Entity:GetID()
    return self.netId
end

---Returns the class builder of the entity.
---@nodiscard
---@return table
function Entity:GetClass()
    return _G[self.__name]
end

---Checks if the given class matches the entity's class.
---@param class table the class to check against
---@return boolean true if the class matches, false otherwise
function Entity:IsA(class)
    return self:GetClass().__name == class.__name
end