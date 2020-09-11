rootProject.name = "Pimp My Hair"

include(":app")
include(":libraries:archi")
include(":libraries:pratik")

buildCache {
    local {
        isEnabled = true
    }
}
