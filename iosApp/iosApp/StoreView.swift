import Foundation
import SwiftUI
import shared

struct StoreView : View {
    let mainViewModel: MainViewModel

    // The ViewModel exposes a StateFlow.
    // We collect() the StateFlow into State, which can be used in SwiftUI.
    @State
    var cartUIState: CartUiState = CartUiState(cartDetails: [])

    @State
    private var expanded = false

    var body: some View {
        VStack {
            HStack {
                let total = cartUIState.cartDetails.reduce(0) { $0 + ($1.count) }
                Text("Cart has \(total) items").padding()
                Spacer()
                Button {
                    expanded.toggle()
                } label: {
                    if (expanded) {
                        Text("collapse")
                    } else {
                        Text("expand")
                    }
                }.padding()
            }
            if (expanded) {
                CartDetailsView(mainViewModel: mainViewModel)
            }
        }
        .collect(flow: self.mainViewModel.cartUiState, into: $cartUIState)
    }
}

struct CartDetailsView: View {
    let mainViewModel: MainViewModel

    // The ViewModel exposes a StateFlow.
    // We collect() the StateFlow into State, which can be used in SwiftUI.
    @State
    var cartUIState: CartUiState = CartUiState(cartDetails: [])

    var body: some View {
        VStack {
            ForEach(cartUIState.cartDetails, id: \.fruittie.id) { item in
                Text("\(item.fruittie.name): \(item.count)")
            }
        }
        .collect(flow: mainViewModel.cartUiState, into: $cartUIState)
    }
}
