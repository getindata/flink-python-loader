from pyflink.datastream.stream_execution_environment import StreamExecutionEnvironment
from pyflink.table import DataTypes, CsvTableSink, StreamTableEnvironment


def tutorial():
    env = StreamExecutionEnvironment.get_execution_environment()
    env.set_parallelism(1)

    field_names = ['a', 'b', 'c']
    field_types = [DataTypes.BIGINT(), DataTypes.STRING(), DataTypes.STRING()]

    t_env = StreamTableEnvironment.create(env)
    t_env.register_table_sink(
        'Results',
        CsvTableSink(field_names, field_types, '/tmp/test.csv'))
    t_env.insert_into('Results', t_env.from_elements([(1, 'Hi', 'Hello')], ['a', 'b', 'c']))

    t_env.execute('test_stream_execute')


if __name__ == '__main__':
    tutorial()
