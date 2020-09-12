rootProject.name = "Pimp My Hair"

include(":app")
include(":libraries:archi")
include(":libraries:pratik")
include(":libraries:network")

buildCache {
    local {
        isEnabled = true
    }
}
