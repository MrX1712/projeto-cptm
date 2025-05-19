import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import Home from './app/home.jsx';
import Ajuda from './app/index.jsx';

const Stack = createNativeStackNavigator();

export default function Home() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Ajuda">
        <Stack.Screen name="Ajuda" component={Ajuda} options={{ title: 'Ajuda' }} />
        <Stack.Screen name="Home" component={Home} options={{ title: 'Página Inicial' }} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
