setmetatable(_G, {
    __index = function (t, k)
        local v = rawget(t, k)
        if v then return v end
        if REFX_FIND(rawget(_G, "REFX_ID"), k) then
            local res = {}
            res.refx = k
            setmetatable(res, {
                __call = function (t, ...)
                    return REFX_CALL(rawget(_G, "REFX_ID"), 0, rawget(t, "refx"), ...)
                end
            })
            return res
        end
    end
})