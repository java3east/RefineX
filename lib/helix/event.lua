---@class Event
Event = {}

---Registers a net event
---@param name string the name of the event to register
---@param cb fun(...: any) the function to call when the event is triggered
function Event.SubscribeRemote(name, cb)
    REFX_REGISTER_EVENT(name, cb, true)
end