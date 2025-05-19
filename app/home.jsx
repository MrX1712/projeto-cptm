console.log("CARREGOU HOME");
import { Image, Pressable, StyleSheet, Text, View } from 'react-native';

export default function Home({ navigation }) {
  console.log("navigation:", navigation); 

  return (
    <View style={styles.container}>
      <View style={styles.navbar}>
        <Image style={styles.logo} source={require('./logocptm.png')} />
      </View>

      <Text style={styles.title}>Bem-vindo ao App!</Text>
      <Pressable
        style={styles.button}
        onPress={() => navigation.navigate('Ajuda')}
      >
        <Text style={styles.buttonText}>Ir para Ajuda</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    backgroundColor: "#fff",
  },
  navbar: {
    paddingVertical: 24,
    paddingHorizontal: 24,
    backgroundColor: "#EE3338",
    width: '100%',
    height: '10%',
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 50,
  },
  logo: {
    width: 70,
    height: 70,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 40,
  },
  button: {
    backgroundColor: "#EE3338",
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 10,
  },
  buttonText: {
    color: "#fff",
    fontWeight: "bold",
    fontSize: 16,
  },
});
