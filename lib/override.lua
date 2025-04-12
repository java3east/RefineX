function print(...)
    local args = {}
    for i = 1, select('#', ...) do
        args[i] = tostring(select(i, ...))
    end
    REFX_PRINT(args)
end