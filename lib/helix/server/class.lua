Class = {}

function Class.create(obj)
    obj.__index = obj

    setmetatable(obj, {
        __call = function (t, ...)
            local o = {}
            setmetatable(o, obj)
            o:initialize(...)
            return o
        end
    })
end
