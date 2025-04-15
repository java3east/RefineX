---@class vhandler
---@field callback fun(...: any) the function to call
---@field isNet boolean true if the event is a network event

---@class vevent
---@field name string the name of the event
---@field isNet boolean true if the event is a network event
---@field source number the sender of the event
---@field destination number the event destination
---@field data any[] the event data

---@type table<string, vhandler[]>
local listeners = {}

---@param name string
---@param callback fun(...: any)
---@param isNet boolean
function REFX_REGISTER_EVENT(name, callback, isNet)
    local handers = listeners[name] or {}
    table.insert(handers, { callback = callback, isNet = isNet })
    listeners[name] = handers
end

---@param event vevent
function REFX_DISPATCH_EVENT(event)
    local handlers = listeners[event.name] or {}
    for _, handler --[[@as vhandler]] in ipairs(handlers) do
        if event.isNet and not handler.isNet then
            -- todo: log error
            goto continue
        end

        handler.callback(table.unpack(event.data))
        ::continue::
    end
end