@file:Suppress("PackageDirectoryMismatch")

package playground.statemachine

import com.tinder.StateMachine
import playground.shouldBe

/**
 * Tinder/StateMachine - A kotlin DSL for finite state machine
 *
 * - [Tinder/State machine: Github](https://github.com/Tinder/StateMachine)
 * - [State diagram](https://user-images.githubusercontent.com/6810202/95701244-73211700-0c1f-11eb-95f1-ce507b25a3bb.png)
 */
fun main() {
    println("# Tinder/State machine - A kotlin DSL for finite state machine")

    machineState.state shouldBe SprintState.ToStart

    machineState.transition(MeetingEvent.Planning)

    machineState.state shouldBe SprintState.InProgress

    machineState.transition(MeetingEvent.Daily)
    machineState.transition(MeetingEvent.Daily)
    machineState.transition(MeetingEvent.Review)
    machineState.transition(MeetingEvent.Retrospective)

    machineState.state shouldBe SprintState.Done

    messages shouldBe listOf(
        "Backlog was updated.",
        "All impediments were identified.",
        "All impediments were identified.",
        "External feedback was received.",
        "Internal feedback was received.",
    )
}

sealed class SprintState {
    object ToStart : SprintState()
    object InProgress : SprintState()
    object InReview : SprintState()
    object Done : SprintState()
}

sealed class MeetingEvent {
    object Planning : MeetingEvent()
    object Daily : MeetingEvent()
    object Review : MeetingEvent()
    object Retrospective : MeetingEvent()
}

sealed class SideEffect {
    object UpdatedBacklog : SideEffect()
    object IdentifiedImpediments : SideEffect()
    object ExternalFeedback : SideEffect()
    object InternalFeedback : SideEffect()
}

val machineState = StateMachine.create<SprintState, MeetingEvent, SideEffect> {
    initialState(SprintState.ToStart)

    state<SprintState.ToStart> {
        on<MeetingEvent.Planning> {
            transitionTo(SprintState.InProgress, SideEffect.UpdatedBacklog)
        }
    }
    state<SprintState.InProgress> {
        on<MeetingEvent.Daily> {
            transitionTo(SprintState.InProgress, SideEffect.IdentifiedImpediments)
        }
        on<MeetingEvent.Review> {
            transitionTo(SprintState.InReview, SideEffect.ExternalFeedback)
        }
    }
    state<SprintState.InReview> {
        on<MeetingEvent.Retrospective> {
            transitionTo(SprintState.Done, SideEffect.InternalFeedback)
        }
    }
    state<SprintState.Done> {
        on<MeetingEvent.Planning> {
            transitionTo(SprintState.InProgress, SideEffect.UpdatedBacklog)
        }
    }
    onTransition { transition ->
        val validTransition = transition as? StateMachine.Transition.Valid ?: return@onTransition
        when (validTransition.sideEffect) {
            SideEffect.UpdatedBacklog -> log("Backlog was updated.")
            SideEffect.IdentifiedImpediments -> log("All impediments were identified.")
            SideEffect.ExternalFeedback -> log("External feedback was received.")
            SideEffect.InternalFeedback -> log("Internal feedback was received.")
        }
    }
}

private val messages = mutableListOf<String>()

private fun log(message: String) {
    messages += message
}
