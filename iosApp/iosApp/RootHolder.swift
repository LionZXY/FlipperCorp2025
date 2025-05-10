import ComposeApp
import Foundation

class RootHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let componentContext: ComponentContext
    private let stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)
    let backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()

    init(){
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        componentContext = DefaultComponentContext(
            lifecycle: lifecycle,
            stateKeeper: stateKeeper,
            instanceKeeper: nil,
            backHandler: backDispatcher
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}
