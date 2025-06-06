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

---@type table<vevent>
local waiting = {}

---@param fullPath string
---@return string
local function trimToResourcePath(fullPath)
    local startIndex = fullPath:find(REFX_RESOURCE_PATH, 1, true)
    if startIndex then
        return fullPath:sub(startIndex)
    end
    return fullPath
end

---@param name string
---@param callback fun(...: any)
---@param isNet boolean
function REFX_REGISTER_EVENT(name, callback, isNet)
    local handers = listeners[name] or {}
    local info = debug.getinfo(3, "Sl")
    local path = trimToResourcePath(info.short_src)
    table.insert(handers, { callback = callback, isNet = isNet, registeredAt = {
            file = path,
            line = info.currentline
        }}
    )
    listeners[name] = handers
end

---@param event vevent
local function process(event)
    local handlers = listeners[event.name] or {}
    for _, handler --[[@as vhandler]] in ipairs(handlers) do
        if event.isNet and not handler.isNet then
            REFX_ERROR("WARNING", "Event '" .. event.name .. "' is a network event, but handler is not",
            "register the event as a network event.", {handler.registeredAt})
            goto continue
        elseif event.isNet ~= handler.isNet then
            REFX_ERROR("WARNING", "Event '" .. event.name .. "' is a local event, but handler is a network event (might cause security issues)",
            "register the event as a local event.", {handler.registeredAt})
        end

        handler.callback(table.unpack(event.data))
        ::continue::
    end
end

function REFX_EV_MAN_TICK()
    for _, event --[[@as vevent]] in ipairs(waiting) do
        process(event)
    end
    waiting = {}
end

---@param event vevent
function REFX_DISPATCH_EVENT(event, instant)
    if listeners[event.name] == nil then
        return false
    end

    if not instant then
        table.insert(waiting, event)
        return true
    end

    process(event)
    return true
end