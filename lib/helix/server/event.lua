---@class Event
Events = {}

---Registers a net event
---@param name string the name of the event to register
---@param cb fun(...: any) the function to call when the event is triggered
function Events.SubscribeRemote(name, cb)
    REFX_REGISTER_EVENT(name, cb, true)
end

---@param name string
---@vararg any
function Events.CallRemote(name, clientId, ...)
    REFX_CALL_REMOTE(name, -1, clientId, {...})
end