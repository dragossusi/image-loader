package ro.dragossusi.kil.error

class DataNotFoundException(
    name: String
) : Exception("$name not found")