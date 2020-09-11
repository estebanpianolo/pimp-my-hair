package com.etienne.libraries.archi.nucleus

interface Action
object DoNothing : Action
object Initialization : Action

@Deprecated("You should directly use Action interface")
interface ExternalAction : Action

@Deprecated("You should directly use Action interface")
interface InternalAction : Action
